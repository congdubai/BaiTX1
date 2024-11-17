package ads.objects;

import java.util.ArrayList;

public class UserResponse {
    private ArrayList<UserObject> users;
    private int totalUsers;

    public UserResponse(ArrayList<UserObject> users, int totalUsers) {
        this.users = users;
        this.totalUsers = totalUsers;
    }

    public ArrayList<UserObject> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserObject> users) {
        this.users = users;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }
}
