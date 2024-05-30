package com.example.kafka_websocket.model;


import lombok.Data;


@Data

public class Employee {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String dept;
}