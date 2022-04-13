package com.gmail.pavlovsv93.rxjava2dagger2.utils

import android.os.Handler


data class Subscriber<T>(
	private val handler: Handler,
	private val callback: (T) -> Unit
) {
	fun invoke(value: T) {
		handler.post {
			callback.invoke(value)
		}
	}
}

class Subscriptions<T> {
	private var subscribers: MutableSet<Subscriber<T>?> = mutableSetOf()
	public var value: T? = null
		private set
	private var progressFlag: Boolean = false

	fun subscribe(
		handler: Handler,
		callback: (T) -> Unit
	) {
		val subscriber = Subscriber(handler, callback)
		if (progressFlag) {
			value?.let { subscriber.invoke(it) }
		}
		this.subscribers.add(subscriber)
	}

	fun unsubscribeAll() {
		progressFlag = false
		subscribers.clear()
	}

	fun post(value: T?) {
		progressFlag = true
		this.value = value
		subscribers.forEach { subscriber ->
			if (value != null) {
				subscriber?.invoke(value)
			}
		}
	}
}