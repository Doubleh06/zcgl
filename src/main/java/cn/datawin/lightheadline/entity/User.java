package cn.datawin.lightheadline.entity;


/**
 * user
 *
 * @author fonlin
 * @date 2018/4/19
 */
public class User extends BaseEntity {

    private String username;

    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 是否启用
     */
    private Boolean enable;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
