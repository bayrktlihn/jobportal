package io.bayrktlihn.jobportal.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "job_post_activity")
@Getter
@Setter
@NoArgsConstructor
public class JobPostActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "job_location_id")
    private JobLocation jobLocation;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "job_company_id")
    private JobCompany jobCompany;

    @Transient
    private Boolean active;
    @Transient
    private Boolean saved;

    @Column(length = 10_000)
    private String descriptionOfJob;

    private String jobType;

    private String salary;

    private String remote;

    @Temporal(TemporalType.TIMESTAMP)
    private Date postedDate;

    private String jobTitle;
}
