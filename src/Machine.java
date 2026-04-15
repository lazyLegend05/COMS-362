
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Machine {

	private String type;
	private String id;
	private ArrayList<LocalDateTime[]> bookedTimes = new ArrayList<LocalDateTime[]>();

	public Machine(String id, String type) {
		this.id = id;
		this.type = type;
	}
	
	public String getId() {return id;}
	
	public String getType() {return type;}
	
	public boolean isBooked(LocalDateTime start, LocalDateTime end) {
		
		boolean free = true;
		
		for (LocalDateTime[] block: bookedTimes) {
			
			if (!(start.isBefore(block[0]) && end.isBefore(block[0]))) {
				if (!(start.isAfter(block[1]) && end.isAfter(block[1]))) {
					
					free = false;
					
				}
			}
		}
		
		return !free;
		
	}
	
	public void bookTime(LocalDateTime start, LocalDateTime end) {
		
		if (!isBooked(start, end)) {
			LocalDateTime[] slot = {start, end};
			bookedTimes.add(slot);
		}
		
	}

	
}
