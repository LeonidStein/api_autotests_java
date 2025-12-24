package com.github.leonidstein.data;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FakeDataManager extends FakeData {

    private static String randomStringBuilder(final int length, final String string) {

        if (length < 0) {
            throw new IllegalArgumentException(String.format(
                    "Параметр length должен быть больше 0, но получен: %d", length));
        }

        return ThreadLocalRandom.current()
                                .ints(length, 0, string.length())
                                .mapToObj(element -> String.valueOf(string.charAt(element)))
                                .collect(Collectors.joining());
    }

    public static int getRandomIntFromTo(final int origin, final int bound) {

        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    public static String getLogin() {

        final int length = 10;

        return getRandomString(length) + new Faker().internet().emailAddress();
    }

    public static String getPassword() {

        final int length = 10;

        return getRandomString(length) + new Faker().internet().password();
    }

    public static String getInvalidLongPassword() {

        return randomStringBuilder(INVALID_PASSWORD_LENGTH, RANDOM_STRING);
    }

    public static String getValidLongPasswordBoundaryValue() {

        return randomStringBuilder(INVALID_PASSWORD_LENGTH - 1, RANDOM_STRING);
    }

    public static String getInvalidLongLogin() {

        return randomStringBuilder(INVALID_LOGIN_LENGTH, RANDOM_STRING);
    }

    public static String getEmptyString() {

        return "";
    }

    public static String getNullString() {

        return null;
    }

    public static boolean getRandomBoolean() {

        return ThreadLocalRandom.current().nextBoolean();
    }

    public static String getDescription() {

        return DESCRIPTION_ARRAY[ThreadLocalRandom.current().nextInt(DESCRIPTION_ARRAY.length)];
    }

    public static String getDescriptionForUpdate() {

        return new Faker().book().title();
    }

    public static double getPrice() {

        return Math.round(ThreadLocalRandom.current().nextDouble(MIN_PRICE, MAX_PRICE + 1));
    }

    public static double getPriceForUpdate() {

        return Math.round(ThreadLocalRandom.current().nextDouble(MIN_PRICE_FOR_UPDATE, MAX_PRICE_FOR_UPDATE + 1));
    }

    public static int getRating() {

        return ThreadLocalRandom.current().nextInt(MIN_RATING, MAX_RATING + 1);
    }

    public static int getRatingForUpdate() {

        return ThreadLocalRandom.current().nextInt(MIN_RATING_FOR_UPDATE, MAX_RATING_FOR_UPDATE + 1);
    }

    public static String getDlcName() {

        return DLC_ARRAY[ThreadLocalRandom.current().nextInt(DLC_ARRAY.length)];
    }

    public static String getDlcForUpdate() {

        return new Faker().elderScrolls().firstName();
    }

    public static int getHardDrive() {

        return HARD_DRIVE_ARRAY[ThreadLocalRandom.current().nextInt(HARD_DRIVE_ARRAY.length)];
    }

    public static String getOSName() {

        return OS_NAME_ARRAY[ThreadLocalRandom.current().nextInt(OS_NAME_ARRAY.length)];
    }

    public static int getRamGB() {

        return RAM_GB_ARRAY[ThreadLocalRandom.current().nextInt(RAM_GB_ARRAY.length)];
    }

    public static String getVideoCard() {

        return VIDEO_CARD_ARRAY[ThreadLocalRandom.current().nextInt(VIDEO_CARD_ARRAY.length)];
    }

    public static String getCompany() {

        return COMPANY_ARRAY[ThreadLocalRandom.current().nextInt(COMPANY_ARRAY.length)];
    }

    public static String getCompanyForUpdate() {

        return new Faker().company().name();
    }

    public static String getGenre() {

        return GENRE_ARRAY[ThreadLocalRandom.current().nextInt(GENRE_ARRAY.length)];
    }

    public static String getGenreForUpdate() {

        return new Faker().book().genre();
    }

    public static String getPublishDate() {

        return LocalDateTime.now().toString();
    }

    public static String getPublishDateForUpdate() {

        final long now = Instant.now().toEpochMilli();
        final long yearAgo = Instant.now().minusSeconds(365L * 24 * 3600).toEpochMilli();
        final long randomMillis = ThreadLocalRandom.current().nextLong(yearAgo, now);
        final Instant randomInstant = Instant.ofEpochMilli(randomMillis);

        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                .withZone(ZoneOffset.UTC)
                                .format(randomInstant);
    }

    public static List<String> getTags() {

        return Arrays.asList(
                TAG_ARRAY[ThreadLocalRandom.current().nextInt(TAG_ARRAY.length)],
                TAG_ARRAY[ThreadLocalRandom.current().nextInt(TAG_ARRAY.length)]
        );
    }

    public static String getTitle() {

        return TITLE_ARRAY[ThreadLocalRandom.current().nextInt(TITLE_ARRAY.length)];
    }

    public static String getTitleForUpdate() {

        return new Faker().book().title();
    }

    public static String getRandomLetter(final int length) {

        return randomStringBuilder(length, ALPHABET);
    }

    public static String getRandomStringNumber(final int length) {

        return randomStringBuilder(length, NUMERIC_CHARS);
    }

    public static String getRandomSpecChar(final int length) {

        return randomStringBuilder(length, SPECIAL_CHARS);
    }

    public static String getRandomString(final int length) {

        return randomStringBuilder(length, RANDOM_STRING);
    }
}
