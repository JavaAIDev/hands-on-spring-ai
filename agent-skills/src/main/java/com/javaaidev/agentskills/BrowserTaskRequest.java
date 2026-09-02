package com.javaaidev.agentskills;

import jakarta.validation.constraints.NotBlank;

public record BrowserTaskRequest(@NotBlank String task) {
}
