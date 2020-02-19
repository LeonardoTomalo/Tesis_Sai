package ec.edu.upse.facsistel.gitwym.sai.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelpClass<k,v,c> {
	private k key;
	private v value;
	private c constant;
}
