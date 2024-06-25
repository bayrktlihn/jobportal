package io.bayrktlihn.jobportal.dto.projection;

public interface RecruiterJobProjection {
    Long getJobPostActivityUserId();

    String getJobTitle();

    Long getJobLocationId();

    String getJobLocationCity();

    String getJobLocationState();

    String getJobLocationCountry();

    Long getJobCompanyId();

    String getJobCompanyName();
}
