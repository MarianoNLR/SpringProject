package com.informatorio.finalproject.dto;

import java.time.LocalDate;
import java.util.Objects;

public class AuthorDTO {

    private String firstName;

    private String lastName;

    private String fullName;

    private LocalDate createdAt;

    public AuthorDTO(String firstName, String lastName, String fullName, LocalDate createdAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.createdAt = createdAt;
    }

    public AuthorDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDTO authorDTO = (AuthorDTO) o;
        return Objects.equals(firstName, authorDTO.firstName) && Objects.equals(lastName, authorDTO.lastName) && Objects.equals(fullName, authorDTO.fullName) && Objects.equals(createdAt, authorDTO.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, fullName, createdAt);
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
