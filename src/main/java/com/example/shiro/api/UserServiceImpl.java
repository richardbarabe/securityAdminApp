package com.example.shiro.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public void create(String username, String... roles) {
		UserDTO user = new UserDTO();
		user.setUsername(username);
		user.setRoles(roles);
		users.add(user);
	}

	@Override
	public UserDTO get(String username) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(String username) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(UserDTO user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<UserDTO> queryByUsername(String searchString, int pageNumber, int pageSize) {
		int start = (pageNumber * pageSize);
		int end = (start + pageSize);

		List<UserDTO> found;
		ArrayList<UserDTO> usersSnapshot = new ArrayList<>(users);
		if (searchString.length() == 0) {
			if (end > usersSnapshot.size())
				end = usersSnapshot.size();
			if (end < start)
				start = end;
			found = usersSnapshot.subList(start, end);
			return found;
		} else {
			found = getByUsername(usersSnapshot, searchString);
			if (end > found.size())
				end = found.size();
			if (end < start)
				start = end;
			return found.subList(start, end);
		}
	}

	private static List<UserDTO> getByUsername(List<UserDTO> users, String searchString) {
		List<UserDTO> found = new LinkedList<>();
		for (UserDTO user : users) {
			if (user.getUsername().contains(searchString)) {
				found.add(user);
			}
		}
		return found;
	}

	private static Set<UserDTO> users = new ConcurrentSkipListSet<>(new Comparator<UserDTO>() {

		@Override
		public int compare(UserDTO user1, UserDTO user2) {
			return user1.getUsername().compareTo(user2.getUsername());
		}
		
	});
	static {
		users.add(new UserDTO("AAA", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("BBB", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("CCC", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("DDD", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("EEE", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("FFF", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("GGG", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("HHH", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("III", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("JJJ", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("KKK", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("LLL", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("MMM", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("NNN", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("OOO", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("PPP", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("QQQ", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("RRR", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("SSS", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("TTT", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("UUU", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("VVV", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("WWW", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("XXX", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("YYY", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("ZZZ", "ADMIN", "SYSTEM_ADMIN", "USER"));

		users.add(new UserDTO("aaa", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("bbb", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("ccc", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("ddd", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("eee", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("fff", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("ggg", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("hhh", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("iii", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("jjj", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("kkk", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("lll", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("mmm", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("nnn", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("ooo", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("ppp", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("qqq", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("rrr", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("sss", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("ttt", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("uuu", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("vvv", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("www", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("xxx", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("yyy", "ADMIN", "SYSTEM_ADMIN", "USER"));
		users.add(new UserDTO("zzz", "ADMIN", "SYSTEM_ADMIN", "USER"));
	}
	
}
