package lld.elevator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Floor {
	int floorNum;
	boolean isUpButtonPressed;
	boolean isDownButtonPressed;
}
