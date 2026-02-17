package com.vlad.demo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UrlRequestDTO(@NotBlank(message = "Empty url") @Pattern(
        regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?$",
        message = "It doesn't look like a normal link."
) @Schema(description = "Long url to shorten.", example = "https://google.com") String originalUrl){}
