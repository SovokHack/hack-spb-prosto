package com.hack.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class ExternalEventDto {
    private Integer id;
    private Boolean isFavorite;
    private OrganizerDTO organizer;
    private String organizerAddress;
    private String organizerAddressEng;
    private String organizerSupervisorFio;
    private String organizerSupervisorFioEng;
    private String organizerSite;
    private String organizerVk;
    private String organizerTelegram;
    private String organizerEmail;
    private Integer freePlaces;
    private Boolean isParticipationAvailable;
    private EventTypeDTO type;
    private String isRequestExisted;
    private List<MemberStatusDTO> membersStatuses;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isActive;
    private String organizerName;
    private List<String> organizerPhone;
    private EventFormatDTO eventFormat;
    private StatusDTO status;
    private String location;
    private String locationEng;
    private AgeRangeDTO membersAges;
    private StatusDTO registrationStatus;
    private PeriodDto registrationPeriod;
    private String registrationComment;
    private Integer placesNumber;
    private List<PeriodDto> periods;
    private Boolean isAvailable;
    private List<Integer> coordinates;
    private String title;
    private String titleEng;
    private String cypher;
    private Boolean isPublished;
    private Date publishedAt;
    private ImageDTO cover;
    private ParentDTO parent;
    private TypeDTO typeof;

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public class OrganizerDTO {
        private String name;
        private int id;
        private String url;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public class EventTypeDTO {
        private int id;
        private String name;
        private String url;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public class MemberStatusDTO {
        private String id;
        private String name;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public class EventFormatDTO {
        private String id;
        private String name;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public class StatusDTO {
        private String id;
        private String name;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public class AgeRangeDTO {
        private int lower;
        private int upper;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public class ImageDTO {
        private int id;
        private String name;
        private String url;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public class ParentDTO {
        private int id;
        private String name;
        private String url;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public class TypeDTO {
        private int id;
        private String name;
        private String url;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public class PeriodDto {
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", timezone = "Europe/Moscow")
        private LocalDateTime lower;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", timezone = "Europe/Moscow")
        private LocalDateTime upper;
    }
}


