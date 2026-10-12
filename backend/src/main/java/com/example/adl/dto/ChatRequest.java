package com.example.adl.dto;

import java.util.List;

public record ChatRequest(List<ChatMessage> messages) {
}
