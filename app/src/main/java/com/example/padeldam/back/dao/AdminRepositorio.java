package com.example.padeldam.back.dao;

import android.util.Log;

import com.example.padeldam.back.entidades.Reserva;
import com.example.padeldam.back.interfaces.IAdmin;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminRepositorio implements IAdmin {
    private final FirebaseFirestore bd;

    private static final String TAG = AdminRepositorio.class.getName();


    public AdminRepositorio(FirebaseFirestore bd) {
        this.bd = bd;
    }



    @Override
    public Task<Boolean> isAdmin(String email) {
        return bd.collection("admins")
                .whereEqualTo("email", email)
                .get()
                .continueWith( task -> {
                            if (task.isSuccessful()) {
                                return !task.getResult().isEmpty();
                            }
                            else {
                                return false;
                            }
                        }
                );
    }
}
