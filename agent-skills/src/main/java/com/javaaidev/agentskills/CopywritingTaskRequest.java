package com.javaaidev.agentskills;

import jakarta.validation.constraints.NotBlank;

public record CopywritingTaskRequest(@NotBlank String task) {
}
