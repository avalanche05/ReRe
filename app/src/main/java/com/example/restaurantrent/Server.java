package com.example.restaurantrent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.restaurantrent.activities.ChatActivity;
import com.example.restaurantrent.activities.MainActivity;
import com.example.restaurantrent.services.MessageService;
import com.example.restaurantrent.services.RentService;
import com.example.restaurantrent.services.RestaurantService;
import com.example.restaurantrent.services.TableService;
import com.example.restaurantrent.services.UserService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

// класс для отправки запросов на сервер
public class Server {

    // метод для авторизации пользователя
    public static void loginUser(final Activity activity, final String email, final String password, final ProgressBar progressBar, final View... views) {
        // выводим загрузку
        showProgressBar(progressBar, views);
        Call<User> callUser = Server.userService.loginUser(email, password);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                // проверяем не пустой ли пользователь
                if(user != null){
                    // проверяем правильность электронной почты и пароля
                    if (user.getEmail() == null) {
                        // уведомляем о том, что пользователь ввёл неверные данные
                        Toast.makeText(activity, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                        // убираем загрузку
                        hideProgressBar(progressBar, views);
                    } else {
                        // добавляем пользователя в SharedPreferences
                        putUserInSharedPreferences(user, activity);
                        // загружаем все рестораны
                        getAllRestaurants();
                        // загружаем все заказы
                        getUserRent(user.getId());
                        // запускаем MainActivity
                        Intent i = new Intent(activity, MainActivity.class);
                        i.putExtra("user", user);
                        activity.startActivity(i);
                        // закрываем текущее activity
                        activity.finish();
                    }
                } else {
                    // уведомляем о внутренней ошибки сервера
                    Toast.makeText(activity, "Ошибка соединения с сервером. Попробуйте заново.", Toast.LENGTH_SHORT).show();
                    // убираем загрузку
                    hideProgressBar(progressBar, views);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // пробуем повторно подключиться к серверу
                loginUser(activity, email, password, progressBar, views);
                t.printStackTrace();
            }
        });
    }

    // метод для регистрации пользователя
    public static void signUpUser(final Activity activity, final String email, final String password, final ProgressBar progressBar, final View... views) {
        // выводим загрузку
        showProgressBar(progressBar, views);
        Call<User> callUser = Server.userService.signUpUser(email, password);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                // проверяем не пустой ли пользователь
                if (user != null) {
                    // проверяем уникальность электронной почты
                    if (user.getEmail() == null) {
                        // уведомляем о том, что пользователь уже зарегистрирован
                        Toast.makeText(activity, "Пользователь с такой электронной почтой уже зарегистрирован", Toast.LENGTH_SHORT).show();
                        hideProgressBar(progressBar, views);
                    } else {
                        // добавляем пользователя в SharedPreferences
                        putUserInSharedPreferences(user, activity);
                        // загружаем все рестораны
                        getAllRestaurants();
                        // загружаем все заказы
                        getUserRent(user.getId());
                        // запускаем MainActivity
                        Intent i = new Intent(activity, MainActivity.class);
                        i.putExtra("user", user);
                        activity.startActivity(i);
                        // закрываем текущее activity
                        activity.finish();
                    }
                } else {
                    // уведомляем о внутренней ошибки сервера
                    Toast.makeText(activity, "Ошибка соединения с сервером. Попробуйте заново.", Toast.LENGTH_SHORT).show();
                    // убираем загрузку
                    hideProgressBar(progressBar, views);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // пробуем повторно подключиться к серверу
                signUpUser(activity, email, password, progressBar, views);
                t.printStackTrace();
            }
        });
    }

    // метод для возвращения списка всех ресторанов
    public static void getAllRestaurants() {
        Call<ArrayList<Restaurant>> callAllRestaurants = restaurantService.getAllRestaurants();
        callAllRestaurants.enqueue(new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                // предварительно очищаем массив
                MainActivity.restaurants.clear();
                // записываем в массив ресторанов ответ от сервера
                for (Restaurant temp : response.body()) {
                    MainActivity.restaurants.add(temp);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // метод для возвращения списка всех заказов пользователя
    public static void getUserRent(Long idUser) {
        Call<ArrayList<Rent>> callUserRent = rentService.getUserRent(idUser);
        callUserRent.enqueue(new Callback<ArrayList<Rent>>() {
            @Override
            public void onResponse(Call<ArrayList<Rent>> call, Response<ArrayList<Rent>> response) {
                // предварительно очищаем массив
                MainActivity.rents.clear();
                // записываем в массив ресторанов ответ от сервера
                for (Rent temp : response.body()) {
                    MainActivity.rents.add(temp);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rent>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // метод для добавления нового заказа
    public static void rentAdd(Rent rent, final Activity activity) {
        Call<String> callString = rentService.rentAdd(rent);
        callString.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // проверяем состояние заказа
                if (response.body().equals("ЗАКАЗ СОЗДАН")) {
                    // уведомляем о успешном создании заказа
                    Toast.makeText(activity, "Столы успешно забронированы!", Toast.LENGTH_SHORT).show();
                    // закрываем activity
                    activity.finish();
                } else {
                    // уведомляем о ошибки создании заказа
                    Toast.makeText(activity, "На это время столы забронированы. Выберите другие столы, или другое время", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // метод для удаления заказа
    public static void rentDelete(Long id, final Activity activity) {
        Call<String> callString = rentService.rentDelete(id);
        callString.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // уведомляем о успешном удалении
                Toast.makeText(activity, "Заказ успешно удалён!", Toast.LENGTH_SHORT).show();
                // обновляем список всех заказов пользователя
                getUserRent(MainActivity.user.getId());
                // закрываем activity
                activity.finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // метод для получения массива столов
    public static void tableGet(Long idRestaurant, final Long idOwner, final Activity fromActivity, final Intent intent) {
        Call<ArrayList<Board>> callTables = tableService.tableGet(idRestaurant);
        callTables.enqueue(new Callback<ArrayList<Board>>() {
            @Override
            public void onResponse(Call<ArrayList<Board>> call, Response<ArrayList<Board>> response) {

                // кладём в Intent массив столов и id владельца ресторана
                intent.putExtra("tables", response.body());
                intent.putExtra("idOwner", idOwner);
                fromActivity.startActivity(intent);

            }

            @Override
            public void onFailure(Call<ArrayList<Board>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    // метод для отправки сообщения
    public static void sendMessage(final Message message) {
        Call<Boolean> callBoolean = messageService.messageSend(message);
        callBoolean.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                // обновляем массив сообщений
                ChatActivity.messages.add(message);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // метод для получения массива сообщений
    public static void getMessage(final Long idRent, final Context context, final Intent intent, final LayoutInflater layoutInflater, final ViewGroup parent) {
        Call<ArrayList<Message>> callMessage = messageService.messageGet(idRent);
        callMessage.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                // заполняем поле ответом от сервера
                ChatActivity.messages = response.body();
                // кладём в интент id заказа
                intent.putExtra("idRent", idRent);
                context.startActivity(intent);

            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    // метод для проверки аутентификации электронной почты
    public static void isConfirm(Long id) {
        Call<Boolean> callBoolean = userService.isConfirm(id);
        callBoolean.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                // обновляем состояние поля
                MainActivity.user.setAuth(response.body());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // метод для добавления пользователя в SharedPreferences
    private static void putUserInSharedPreferences(User user, Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences(Helper.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Helper.APP_PREFERENCES_EMAIL, user.getEmail());
        editor.putString(Helper.APP_PREFERENCES_PASSWORD, user.getPassword());
        editor.apply();
    }

    // метод для отобрадения ProgressBar
    private static void showProgressBar(ProgressBar progressBar, View... views) {
        progressBar.setVisibility(View.VISIBLE);
        for (View temp : views) {
            temp.setVisibility(View.INVISIBLE);
        }
    }

    // метод для закрытия ProgressBar
    private static void hideProgressBar(ProgressBar progressBar, View... views) {
        progressBar.setVisibility(View.INVISIBLE);
        for (View temp : views) {
            temp.setVisibility(View.VISIBLE);
        }
    }

    // инициализация Retrofit
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://restaurant-rent-server.herokuapp.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // инициализация сервисов
    private static UserService userService = retrofit.create(UserService.class);
    private static RestaurantService restaurantService = retrofit.create(RestaurantService.class);
    private static RentService rentService = retrofit.create(RentService.class);
    private static TableService tableService = retrofit.create(TableService.class);
    private static MessageService messageService = retrofit.create(MessageService.class);


}
