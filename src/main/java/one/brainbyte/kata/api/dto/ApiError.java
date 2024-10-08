package one.brainbyte.kata.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Builder
public class ApiError {
    private final String message;
    private final String status;
}
