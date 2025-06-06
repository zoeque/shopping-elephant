package zoeque.elephant.usecase.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The Data transfer object for task.
 *
 * @param itemName      The item name to buy
 * @param executionDate The date with string type,
 *                      formatted as yyyy/MM/dd HH:mm:ss
 */
@JsonDeserialize(as = ShoppingTaskDto.class)
public record ShoppingTaskDto(
        Long identifier,
        String itemName,
        String executionDate
) {
}
