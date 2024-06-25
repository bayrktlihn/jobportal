package io.bayrktlihn.jobportal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recruiter_profile")
@Getter
@Setter
@NoArgsConstructor
public class RecruiterProfile {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    private String firstName;
    private String lastName;
    private String city;
    private String state;
    private String country;
    private String company;
    private String profilePhoto;

    public String fetchPhotosImagePath() {
        if(profilePhoto == null) {
            return null;
        }
        return "/photos/recruiter/" + id + "/" + profilePhoto;
    }
}
