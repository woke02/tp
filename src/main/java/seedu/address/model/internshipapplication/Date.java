package seedu.address.model.internshipapplication;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.validator.DateValidator;

/**
 * Represents a Date in the internship book.
 * Guarantees: immutable; the date is valid as declared in the constructor.
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in the format 'dd/MM/yy' and must be valid.";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");

    private final LocalDate date;

    /**
     * Constructs a {@code Date} with a {@code LocalDate}.
     *
     * @param date A valid {@code LocalDate} object.
     * @throws NullPointerException if the {@code date} is null.
     */
    public Date(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    /**
     * Constructs a {@code Date} from a string in the format 'dd/MM/yy'.
     *
     * @param dateString A string representing a date in the format 'dd/MM/yy'.
     * @throws NullPointerException if the {@code dateString} is null.
     * @throws IllegalArgumentException if the {@code dateString} does not satisfy the format or is invalid.
     */
    public Date(String dateString) {
        requireNonNull(dateString);
        checkArgument(DateValidator.of().validate(dateString), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(dateString, FORMATTER);
    }

    /**
     * Returns the {@code LocalDate} representation of the date.
     *
     * @return The date as a {@code LocalDate} object.
     */
    public LocalDate getValue() {
        return this.date;
    }

    /**
     * Returns a string representation of the date using the 'dd/MM/yy' format.
     *
     * @return The formatted date as a string.
     */
    @Override
    public String toString() {
        return date.format(FORMATTER);
    }

    /**
     * Compares this {@code Date} to another object for equality.
     *
     * @param other The object to compare with.
     * @return True if the object is an instance of {@code Date} and has the same value, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return date.equals(otherDate.date);
    }

    /**
     * Returns the hash code of the date.
     *
     * @return The hash code of the {@code LocalDate} value.
     */
    @Override
    public int hashCode() {
        return date.hashCode();
    }
}