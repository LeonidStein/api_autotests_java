package com.github.leonidstein.builders;

import com.github.leonidstein.models.request.user.create.DlcsItemModel;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import com.github.leonidstein.models.request.user.create.RequirementsModel;
import com.github.leonidstein.models.request.user.create.SimilarDlcModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.github.leonidstein.data.FakeDataManager.getCompany;
import static com.github.leonidstein.data.FakeDataManager.getDescription;
import static com.github.leonidstein.data.FakeDataManager.getDescriptionForUpdate;
import static com.github.leonidstein.data.FakeDataManager.getDlcForUpdate;
import static com.github.leonidstein.data.FakeDataManager.getDlcName;
import static com.github.leonidstein.data.FakeDataManager.getFalseBoolean;
import static com.github.leonidstein.data.FakeDataManager.getGenre;
import static com.github.leonidstein.data.FakeDataManager.getHardDrive;
import static com.github.leonidstein.data.FakeDataManager.getOSName;
import static com.github.leonidstein.data.FakeDataManager.getPrice;
import static com.github.leonidstein.data.FakeDataManager.getPublishDate;
import static com.github.leonidstein.data.FakeDataManager.getRamGB;
import static com.github.leonidstein.data.FakeDataManager.getRandomBoolean;
import static com.github.leonidstein.data.FakeDataManager.getRandomIntegerFrom0ToMaxIntValue;
import static com.github.leonidstein.data.FakeDataManager.getRating;
import static com.github.leonidstein.data.FakeDataManager.getTags;
import static com.github.leonidstein.data.FakeDataManager.getTitle;
import static com.github.leonidstein.data.FakeDataManager.getTrueBoolean;
import static com.github.leonidstein.data.FakeDataManager.getVideoCard;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GameBuilder {

    public static GamesItemModel createdGame() {

        return GamesItemModel.builder()
                                .company(getCompany())
                                .description(getDescription())
                                .dlcs(List.of(getDlcsItem()))
                                .gameId(null)
                                .genre(getGenre())
                                .isFree(getRandomBoolean())
                                .price(getPrice())
                                .publishDate(getPublishDate())
                                .rating(getRating())
                                .requiredAge(getRandomBoolean())
                                .requirements(getRequirements())
                                .tags(getTags())
                                .title(getTitle())
                             .build();
    }

    public static GamesItemModel createdPaidGame() {

        return GamesItemModel.builder()
                                .company(getCompany())
                                .description(getDescription())
                                .dlcs(List.of(getPaidDlcsItem()))
                                .gameId(null)
                                .genre(getGenre())
                                .isFree(false)
                                .price(getPrice())
                                .publishDate(getPublishDate())
                                .rating(getRating())
                                .requiredAge(getRandomBoolean())
                                .requirements(getRequirements())
                                .tags(getTags())
                                .title(getTitle())
                             .build();
    }

    public static GamesItemModel createdGameWithGameId() {

        return GamesItemModel.builder()
                                .company(getCompany())
                                .description(getDescription())
                                .dlcs(List.of(getPaidDlcsItem()))
                                .gameId(getRandomIntegerFrom0ToMaxIntValue())
                                .genre(getGenre())
                                .isFree(false)
                                .price(getPrice())
                                .publishDate(getPublishDate())
                                .rating(getRating())
                                .requiredAge(getRandomBoolean())
                                .requirements(getRequirements())
                                .tags(getTags())
                                .title(getTitle())
                             .build();
    }

    public static GamesItemModel createdFreeGameWithPrice() {

        return GamesItemModel.builder()
                                .company(getCompany())
                                .description(getDescription())
                                .dlcs(List.of(getPaidDlcsItem()))
                                .gameId(null)
                                .genre(getGenre())
                                .isFree(true)
                                .price(getPrice())
                                .publishDate(getPublishDate())
                                .rating(getRating())
                                .requiredAge(getRandomBoolean())
                                .requirements(getRequirements())
                                .tags(getTags())
                                .title(getTitle())
                             .build();
    }

    public static GamesItemModel createdGameWithFreeDlcsWithPrice() {

        return GamesItemModel.builder()
                                .company(getCompany())
                                .description(getDescription())
                                .dlcs(List.of(getFreeDlcsWithPriceItem()))
                                .gameId(null)
                                .genre(getGenre())
                                .isFree(false)
                                .price(getPrice())
                                .publishDate(getPublishDate())
                                .rating(getRating())
                                .requiredAge(getRandomBoolean())
                                .requirements(getRequirements())
                                .tags(getTags())
                                .title(getTitle())
                             .build();
    }

    public static GamesItemModel createdGameForUpdate() {

        return GamesItemModel.builder()
                                .company(getCompany())
                                .description(getDescription())
                                .dlcs(List.of(getDlcsItem()))
                                .gameId(null)
                                .genre(getGenre())
                                .isFree(false)
                                .price(getPrice())
                                .publishDate(getPublishDate())
                                .rating(getRating())
                                .requiredAge(getFalseBoolean())
                                .requirements(getRequirements())
                                .tags(getTags())
                                .title(getTitle())
                             .build();
    }

    public static DlcsItemModel createdUpdatedDlc() {

        return DlcsItemModel.builder()
                                .description(getDescriptionForUpdate())
                                .dlcName(getDlcForUpdate())
                                .isDlcFree(getRandomBoolean())
                                .price(getPrice())
                                .rating(getRating())
                                .similarDlc(getUpdateSimilarDlc())
                            .build();
    }

    private static DlcsItemModel getDlcsItem() {

        return DlcsItemModel.builder()
                                .description(getDescription())
                                .dlcName(getDlcName())
                                .isDlcFree(getRandomBoolean())
                                .price(getPrice())
                                .rating(getRating())
                                .similarDlc(getSimilarDlc())
                            .build();
    }

    private static DlcsItemModel getPaidDlcsItem() {

        return DlcsItemModel.builder()
                                .description(getDescription())
                                .dlcName(getDlcName())
                                .isDlcFree(getFalseBoolean())
                                .price(getPrice())
                                .rating(getRating())
                                .similarDlc(getSimilarDlc())
                            .build();
    }

    private static DlcsItemModel getFreeDlcsWithPriceItem() {

        return DlcsItemModel.builder()
                                .description(getDescription())
                                .dlcName(getDlcName())
                                .isDlcFree(getTrueBoolean())
                                .price(getPrice())
                                .rating(getRating())
                                .similarDlc(getSimilarDlc())
                            .build();
    }

    private static RequirementsModel getRequirements() {

        return RequirementsModel.builder()
                                    .hardDrive(getHardDrive())
                                    .osName(getOSName())
                                    .ramGb(getRamGB())
                                    .videoCard(getVideoCard())
                                .build();
    }

    private static SimilarDlcModel getSimilarDlc() {

        return SimilarDlcModel.builder()
                                  .dlcNameFromAnotherGame(getDlcName())
                                  .isFree(getRandomBoolean())
                              .build();
    }

    private static SimilarDlcModel getUpdateSimilarDlc() {

        return SimilarDlcModel.builder()
                                  .dlcNameFromAnotherGame(getDlcForUpdate())
                                  .isFree(getRandomBoolean())
                              .build();
    }
}
