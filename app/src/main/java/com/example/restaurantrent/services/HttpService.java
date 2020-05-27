package com.example.restaurantrent.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import com.example.restaurantrent.activities.LoginActivity;
import com.example.restaurantrent.activities.SelectDateActivity;
import com.example.restaurantrent.activities.SignUpActivity;
import com.example.restaurantrent.constant.ActConst;
import com.example.restaurantrent.parsers.JsonParser;

import com.example.restaurantrent.activities.MainActivity;

import com.example.restaurantrent.activities.Main3Activity;
import com.example.restaurantrent.builders.ParameterStringBuilder;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HttpService extends Service {

    private static final String DOMAIN = "http://restaurant-rent-server.herokuapp.com";

    public static final String CHANNEL = "GIS_SERVICE";
    public static final String INFO = "INFO";

    @Override
    public void onCreate() {
        // сообщение о создании службы
        System.out.println("СЕРВИС СОЗДАН");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // сообщение о запуске службы
        System.out.println("СЕРВИС ЗАПУЩЕН");
        switch (intent.getIntExtra("act",-1)){
            case ActConst.GET_TABLES_ACT:
                GetTablesTask getTablesTask = new GetTablesTask();
                getTablesTask.execute(intent.getLongExtra("idRestaurant",-1)+"",intent.getBooleanExtra("isRent",false)+"",intent.getLongExtra("idOwner",-1)+"");
                break;
            case ActConst.GET_ALL_RESTAURANTS_ACT:
                GetAllRestaurantsTask getRestaurantsTask = new GetAllRestaurantsTask();
                getRestaurantsTask.execute();
                break;
            case ActConst.SIGNUP_ACT:
                SignUpTask signUpTask = new SignUpTask();
                signUpTask.execute(intent.getStringExtra("email"),intent.getStringExtra("password"));
                break;
            case ActConst.LOGIN_ACT:
                LoginTask loginTask = new LoginTask();
                loginTask.execute(intent.getStringExtra("email"),intent.getStringExtra("password"));
                break;
            case ActConst.GET_USER_ACT:
                GetUserTask getUserTask = new GetUserTask();
                getUserTask.execute(intent.getLongExtra("id",-1)+"");
                break;
            case ActConst.ADD_RENT_ACT:
                AddRentTask addRentTask = new AddRentTask();
                addRentTask.execute(intent.getLongExtra("idUser",-1)+"",intent.getStringExtra("idTables"),intent.getStringExtra("date"),intent.getStringExtra("idOwner"),intent.getStringExtra("time"));
                break;
            case ActConst.GET_RENTS_ACT:
                GetRentsTask getRentsTask = new GetRentsTask();
                getRentsTask.execute(intent.getLongExtra("idUser",-1)+"");
                break;
            case ActConst.DELETE_RENT_ACT:
                DeleteRentTask deleteRentTask = new DeleteRentTask();
                deleteRentTask.execute(intent.getLongExtra("id",-1)+"");
                break;
            default:
                break;
        }


        // создаем объект нашего AsyncTask (необходимо для работы с сетью)
        // запускаем AsyncTask

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        //сообщение об остановке службы
        Toast.makeText(this, "Service stoped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //поток работы с сетью

    private class GetTablesTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN + "/table/get");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                parameters.put("idRestaurant", args[0]);



                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " + status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);

                Main3Activity.tables = JsonParser.getTables(result);

                Intent i = new Intent(HttpService.this,Main3Activity.class);
                i.putExtra("isRent",args[1]);
                i.putExtra("idOwner",args[2]);
                startActivity(i);


            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }
    private class GetAllRestaurantsTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN + "/restaurant/all");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " + status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);

                MainActivity.restaurants = JsonParser.getRestaurants(result);


            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }
    private class SignUpTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN + "/user/add");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                parameters.put("email",args[0]);
                parameters.put("password",args[1]);

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " + status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);
                if(result.contains("Пользователь уже зарегистрирован")){
                    SignUpActivity.signUpActivityThis.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(SignUpActivity.signUpActivityThis, "Пользователь с такой электронной почтой уже зарегистрирован", Toast.LENGTH_LONG).show();
                        }
                    });
                    SignUpActivity.signUpActivityThis.finish();
                    startActivity(new Intent(HttpService.this,SignUpActivity.class));
                }
                SignUpActivity.signUpActivityThis.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(SignUpActivity.signUpActivityThis, "Вы успешно зарегистрированы!", Toast.LENGTH_LONG).show();
                    }
                });

                SignUpActivity.signUpActivityThis.finish();


            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }
    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN + "/user/login");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                parameters.put("email",args[0]);
                parameters.put("password",args[1]);

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " + status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);

                try {
                    long id = Long.parseLong(result);
                    MainActivity.idUser = id;
                    Intent i = new Intent(HttpService.this,HttpService.class);
                    i.putExtra("act",ActConst.GET_USER_ACT);
                    i.putExtra("id",id);
                    startService(i);
                }
                catch (Exception e){
                    e.printStackTrace();
                    LoginActivity.loginActivityThis.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.loginActivityThis,"Неверный логин или пароль",Toast.LENGTH_SHORT).show();
                        }
                    });
                    LoginActivity.loginActivityThis.finish();
                    startActivity(new Intent(HttpService.this,LoginActivity.class));
                }



            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }
    private class GetUserTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN + "/user/get");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                parameters.put("id",args[0]);

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " + status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);

                Intent i = new Intent(HttpService.this,MainActivity.class);
                i.putExtra("info",result);
                startActivity(i);
                LoginActivity.loginActivityThis.finish();



            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }
    private class AddRentTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN + "/rent/add");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                System.out.println(args[0]+args[1]+args[2]);

                parameters.put("idUser",args[0]);
                parameters.put("idTables",args[1]);
                parameters.put("date",args[2]);
                parameters.put("idOwner",args[3]);
                parameters.put("time",args[4]);

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " + status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);

                if(result.equals("Столы уже забронированы")){
                    SelectDateActivity.selectDateActivityThis.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(SelectDateActivity.selectDateActivityThis, "На это время столы заняты. Выберите другой стол или другое время.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    SelectDateActivity.selectDateActivityThis.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(SelectDateActivity.selectDateActivityThis, "Столы успешно забронированы!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i = new Intent(HttpService.this, HttpService.class);
                    i.putExtra("act", ActConst.GET_USER_ACT);
                    i.putExtra("id", MainActivity.idUser);
                    startService(i);
                }


            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }
    private class GetRentsTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN + "/user/rent/get");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                parameters.put("idUser",args[0]);

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " + status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);

                MainActivity.rents = JsonParser.getRents(result);




            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }
    private class DeleteRentTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN + "/rent/delete");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                parameters.put("id",args[0]);

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " + status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);
                Main3Activity.main3ActivityThis.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Main3Activity.main3ActivityThis,"Бронь успешно удалена.",Toast.LENGTH_SHORT).show();
                    }
                });
                Main3Activity.main3ActivityThis.finish();




            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }



}

