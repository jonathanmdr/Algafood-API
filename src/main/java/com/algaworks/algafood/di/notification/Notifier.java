package com.algaworks.algafood.di.notification;

import com.algaworks.algafood.di.model.Customer;

public interface Notifier {

	void notify(Customer customer, String message);

}