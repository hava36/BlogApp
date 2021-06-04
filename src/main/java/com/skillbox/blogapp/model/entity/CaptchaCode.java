package com.skillbox.blogapp.model.entity;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "captcha_code")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class CaptchaCode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(name = "time", nullable = false)
    private Instant time;
    @NotNull
    @Column(name = "code", nullable = false)
    private String code;
    @NotNull
    @Column(name = "secret_code", nullable = false)
    private String secretCode;
    private transient String imageInBase64;

    public CaptchaCode(Instant time, String code, String secretCode, String imageInBase64) {
        this.time = time;
        this.code = code;
        this.secretCode = secretCode;
        this.imageInBase64 = imageInBase64;
    }
}
