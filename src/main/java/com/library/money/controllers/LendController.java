package com.library.money.controllers;

import com.library.money.JWT.JsonWebToken;
import com.library.money.models.Books;
import com.library.money.models.Lend;
import com.library.money.models.Register;
import com.library.money.repository.LendRepository;
import com.library.money.repository.RegisterRepository;
import com.library.money.service.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lend")
public class LendController {

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    LendRepository lendRepository;

    @Autowired
    RegisterRepository registerRepository;

    @Autowired
    JsonWebToken jsonWebToken;

    @PostMapping
    public ResponseEntity<?> lendBook(@RequestParam("bookId")Long bookId, @RequestHeader("Authorization") String JWT){
        //extracting username from JWT
        String username = jsonWebToken.extractUsername(JWT);

        Long userId = registerRepository.getRegisterByUsername(username).getUserId();

        try{
            //passing data through the constructor
            Lend lend = new Lend(new Books(bookId), new Register(userId));

            Lend lendResponse = lendRepository.saveAndFlush(lend);

            return responseHandler.generateResponse(HttpStatus.OK, true, "success.", lendRepository.getOne(lendResponse.getLendId()));

        }catch (Exception e){
            return responseHandler.generateResponse(HttpStatus.FORBIDDEN, false, "Error in lending book.", null);
        }
    }
}
