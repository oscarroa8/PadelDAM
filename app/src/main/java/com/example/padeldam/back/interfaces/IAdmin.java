package com.example.padeldam.back.interfaces;

import com.google.android.gms.tasks.Task;

public interface IAdmin {

    Task<Boolean> isAdmin(String email);
}
