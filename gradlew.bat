<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".Tela_Login"
    android:background="@layout/Background_Tela_Login"
    >

    <TextView
        android:id="@+id/NameProject_id"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="176dp"
        android:lineSpacingExtra="14sp"
        android:text="Connect"
        android:textColor="#FFFFFF"
        android:textSize="54sp"
        android:typeface="monospace"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.391"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/NameProject_id2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Us"
        android:textColor="#FFFFFF"
        android:textSize="54sp"
        android:typeface="monospace"
        app:layout_constraintEnd_toEndOf="@+id/NameProject_id"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="@+id/NameProject_id"
        app:layout_constraintTop_toBottomOf="@+id/NameProject_id" />

    <EditText
        android:id="@+id/Login_Email"
        android:layout_width="700px"
        android:layout_height="100px"
        android:layout_marginTop="80dp"
        android:background="#FFFFFF"
        android:ems="10"
        android:hint="Email"
        android:inputType="textEmailAddress"
        android:textColor="#0B0707"
        android:textColorHint="#090505"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/NameProject_id2" />

    <EditText
        android:id="@+id/Login_Password"
        android:layout_width="700px"
        android:layout_heigh