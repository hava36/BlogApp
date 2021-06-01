package com.skillbox.blogapp.model.entity;

import com.github.cage.Cage;
import com.github.cage.YCage;
import java.io.Serializable;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;
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

/**
 * A CaptchaCode.
 */
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

    public CaptchaCode(String secretCode) {

        Cage cage = new YCage();
        this.time = Instant.now();
        this.code = cage.getTokenGenerator().next();
        this.secretCode = UUID.randomUUID().toString();
        this.imageInBase64 = String.format("data:image/png;base64, %s",
            Base64.getEncoder().encodeToString(cage.draw(secretCode)));

    }

}
