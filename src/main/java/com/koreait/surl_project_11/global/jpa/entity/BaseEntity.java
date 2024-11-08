package com.koreait.surl_project_11.global.jpa.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass // 부모한테 붙는다.
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    public String getModelName() {
        String simpleName = this.getClass().getSimpleName();
        return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
    }
}
