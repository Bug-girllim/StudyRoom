package model.dto;

public class MemberDTO {

    private int memberId;
    private String loginId;
    private String password;
    private String memberName;
    private String phone;
    private String memberRole;

public MemberDTO(){}

    public MemberDTO(int memberId, String loginId, String password, String memberName, String phone, String memberRole) {
        this.memberId = memberId;
        this.loginId = loginId;
        this.password = password;
        this.memberName = memberName;
        this.phone = phone;
        this.memberRole = memberRole;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "memberId=" + memberId +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", memberName='" + memberName + '\'' +
                ", phone='" + phone + '\'' +
                ", memberRole='" + memberRole + '\'' +
                '}';
    }
}
