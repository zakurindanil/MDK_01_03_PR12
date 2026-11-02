package com.example.tools_zakurin;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.network.datas.users.UserCreate;
import com.example.network.datas.users.UserGet;
import com.example.network.datas.users.UserLogin;
import com.example.network.datas.users.UserLogout;
import com.example.network.datas.users.UserSend;
import com.example.network.datas.users.UserUpdate;
import com.example.network.domains.callbacks.MyResponseCallback;
import com.example.network.domains.common.Settings;
import com.example.network.domains.models.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    /**
     * Интеграционный тест для проверки функциональности входа пользователя.
     * Выполняет реальный сетевой запрос к серверу и проверяет успешность авторизации.
     */
    @Test
    public void UserLogin() throws InterruptedException {
        // Используем массив с одним элементом для обхода ограничения на изменение переменных
        final Boolean[] Success = {false};
        // CountDownLatch используется для синхронизации асинхронной операции с тестовым потоком
        // Счетчик = 1 означает, что мы ждем одно событие (завершение запроса)
        CountDownLatch Latch = new CountDownLatch(1);
        // Создание тестового пользователя с валидными учетными данными
        User User = new User("testing@mail.ru", "Asdfg123*");
        // Создание и запуск асинхронной задачи входа
        new UserLogin(
                User,  // Данные пользователя для входа
                new MyResponseCallback() {  // Анонимный класс для обработки результатов
                    // Вызывается при успешном входе в систему
                    @Override
                    public void onCompile(String result) {
                        // Логирование успешного ответа для отладки
                        Log.d("USER LOGIN", result);
                        // Установка флага успеха
                        Success[0] = true;
                        // Уменьшение счетчика Latch - сигнал о завершении теста
                        Latch.countDown();
                    }
                    // Вызывается при ошибке входа в систему
                    @Override
                    public void onError(String error) {
                        // Логирование ошибки для отладки
                        Log.e("USER LOGIN", error);
                        // Уменьшение счетчика Latch - сигнал о завершении теста
                        Latch.countDown();
                    }
                }
        ).execute();  // Запуск асинхронной задачи
        // Ожидание завершения асинхронной операции с таймаутом 60 секунд
        Boolean Completed = Latch.await(60, TimeUnit.SECONDS);
        // Проверка результата теста:
        // - Success[0] должен быть true (успешный вход)
        // - Если false, тест провалится с AssertionError
        assertTrue(Success[0]);
    }

    @Test
    public void UserCreate() throws InterruptedException {
        final Boolean[] Success = {false};
        CountDownLatch Latch = new CountDownLatch(1);
        User User = new User("test3@mail.ru", "Asdfg123*", "Тестовый", "Пользователь", "Системы", 0);
        new UserCreate(
                User,
                new MyResponseCallback() {
                    @Override
                    public void onCompile(String result) {
                        Log.d("USER CREATE", result);
                        Success[0] = true;
                        Latch.countDown();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("USER CREATE", error);
                        Success[0] = false;
                        Latch.countDown();
                    }
                }
        ).execute();
        Boolean Completed = Latch.await(60, TimeUnit.SECONDS);
        assertTrue(Success[0]);
    }

    @Test
    public void UserUpdate() throws InterruptedException {
        final Boolean[] Success = {false};
        CountDownLatch Latch = new CountDownLatch(1);
        User User = new User("test3@mail.ru", "Asdfg123*", "Тестовый", "Пользователь", "Системы", 0);
        new UserUpdate(
                User,
                Settings.DEMO_TOKEN,
                new MyResponseCallback() {
                    @Override
                    public void onCompile(String result) {
                        Log.d("USER UPDATE", result);
                        Success[0] = true;
                        Latch.countDown();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("USER UPDATE", error);
                        Success[0] = false;
                        Latch.countDown();
                    }
                }
        ).execute();
        Boolean Completed = Latch.await(60, TimeUnit.SECONDS);
        assertTrue(Success[0]);
    }

    @Test
    public void UserGet() throws InterruptedException {
        final Boolean[] Success = {false};
        CountDownLatch Latch = new CountDownLatch(1);
        new UserGet(
                Settings.DEMO_TOKEN,
                new MyResponseCallback() {
                    @Override
                    public void onCompile(String result) {
                        Log.d("USER GET", result);
                        Success[0] = true;
                        Latch.countDown();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("USER GET", error);
                        Success[0] = false;
                        Latch.countDown();
                    }
                }
        ).execute();
        Boolean Completed = Latch.await(60, TimeUnit.SECONDS);
        assertTrue(Success[0]);
    }

    @Test
    public void UserLogout() throws InterruptedException {
        final Boolean[] Success = {false};
        CountDownLatch Latch = new CountDownLatch(1);
        new UserLogout(
                Settings.DEMO_TOKEN,
                new MyResponseCallback() {
                    @Override
                    public void onCompile(String result) {
                        Log.d("USER LOGOUT", result);
                        Success[0] = true;
                        Latch.countDown();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("USER LOGOUT", error);
                        Success[0] = false;
                        Latch.countDown();
                    }
                }
        ).execute();
        Boolean Completed = Latch.await(60, TimeUnit.SECONDS);
        assertTrue(Success[0]);
    }

    @Test
    public void UserSend() throws InterruptedException {
        final Boolean[] Success = {false};
        CountDownLatch Latch = new CountDownLatch(1);
        new UserSend(
                "testing@mail.ru",
                new MyResponseCallback() {
                    @Override
                    public void onCompile(String result) {
                        Log.d("USER SEND", result);
                        Success[0] = true;
                        Latch.countDown();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("USER SEND", error);
                        Success[0] = false;
                        Latch.countDown();
                    }
                }
        ).execute();
        Boolean Completed = Latch.await(60, TimeUnit.SECONDS);
        assertTrue(Success[0]);
    }
}
