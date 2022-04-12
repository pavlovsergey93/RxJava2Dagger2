package com.gmail.pavlovsv93.rxjava2dagger2.utils

private typealias Subscriber<T> = (T?) -> Unit

class Subscriptions<T> {
	private var subscribers: MutableSet<Subscriber<T>?> = mutableSetOf()
	public var value: T? = null
		private set
	private var progressFlag: Boolean = false

	fun subscribe(subscriber: Subscriber<T>) {
		if (progressFlag) {
			subscriber.invoke(value)
		}
		this.subscribers.add(subscriber)
	}

	fun unsubscribe(subscriber: Subscriber<T>) {
		subscribers.remove(subscriber)
		progressFlag = false
	}

	fun unsubscribeAll() {
		progressFlag = false
		subscribers.clear()
	}

	fun post(value: T?) {
		progressFlag = true
		this.value = value
		subscribers.forEach { subscriber ->
			subscriber?.invoke(value)
		}
	}
}