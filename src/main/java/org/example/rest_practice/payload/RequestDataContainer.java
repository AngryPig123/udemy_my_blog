package org.example.rest_practice.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : org.example.rest_practice.payload
 * fileName       : RequestDataContainer
 * author         : AngryPig123
 * date           : 2024-03-12
 * description    : @RequestBody 로 단일 데이터를 받을 때 사용하기 위한 와퍼 클래스.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-12        AngryPig123       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDataContainer<T> {
    private T data;
}
