package org.jsp.stocks.service;

import org.jsp.stocks.dto.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public interface StockService {

	String register(User user, Model model);

	String register(@Valid User user, BindingResult result);


	String verifyOtp(int id, int otp);

	



}

