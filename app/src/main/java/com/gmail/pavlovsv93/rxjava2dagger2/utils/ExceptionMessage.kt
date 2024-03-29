package com.gmail.pavlovsv93.rxjava2dagger2.utils

import com.gmail.pavlovsv93.rxjava2dagger2.R

enum class ExceptionMessage(val message: String) {

	E201("Аккаунт сохранен успешно!"),
	E202("Аккаунт удален"),
	E400("Логин или email уже используются!"),
	E401("Неверный логин или пароль!"),
	E402("Такой логин уже существует"),
	E403("На этот E-mail зарегистрирован аккаунт"),
	E404("Введены некоректные данные!"),
	E405("Ошибка удаления"),
	E406("Email не задан или используется"),
	E407("Ошибка обновления"),
	E408("Данные не найдены"),
	E409("Пароли не совпадают"),
	E410("Не заполнены все поля!")
}
