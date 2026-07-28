package com.busreserve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    @NotBlank(message = "Bus ID is required")
    private String busId;

    @NotBlank(message = "Travel date is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in YYYY-MM-DD format")
    private String travelDate;

    @NotEmpty(message = "At least one seat must be selected")
    private List<Integer> seatNumbers;

    @NotBlank(message = "Passenger name is required")
    private String passengerName;

    @NotBlank(message = "Passenger email is required")
    @Email(message = "Invalid email format")
    private String passengerEmail;

    @NotBlank(message = "Passenger phone is required")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,15}$", message = "Invalid phone number format")
    private String passengerPhone;
}
