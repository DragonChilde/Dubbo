**gmall-interface(公共接口层)**

所有公共接口层（model，service，exception…）

**Bean模型**

	public class UserAddress implements Serializable {
	    private Integer id;
	    private String userAddress; //用户地址
	    private String userId; //用户id
	    private String consignee; //收货人
	    private String phoneNum; //电话号码
	    private String isDefault; //是否为默认地址    Y-是     N-否
	
	    public UserAddress() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	
	    public UserAddress(Integer id, String userAddress, String userId, String consignee, String phoneNum,
	                       String isDefault) {
	        super();
	        this.id = id;
	        this.userAddress = userAddress;
	        this.userId = userId;
	        this.consignee = consignee;
	        this.phoneNum = phoneNum;
	        this.isDefault = isDefault;
	    }
	
	    public Integer getId() {
	        return id;
	    }
	    public void setId(Integer id) {
	        this.id = id;
	    }
	    public String getUserAddress() {
	        return userAddress;
	    }
	    public void setUserAddress(String userAddress) {
	        this.userAddress = userAddress;
	    }
	    public String getUserId() {
	        return userId;
	    }
	    public void setUserId(String userId) {
	        this.userId = userId;
	    }
	    public String getConsignee() {
	        return consignee;
	    }
	    public void setConsignee(String consignee) {
	        this.consignee = consignee;
	    }
	    public String getPhoneNum() {
	        return phoneNum;
	    }
	    public void setPhoneNum(String phoneNum) {
	        this.phoneNum = phoneNum;
	    }
	    public String getIsDefault() {
	        return isDefault;
	    }
	    public void setIsDefault(String isDefault) {
	        this.isDefault = isDefault;
	    }
	
	}

**Server接口**
	
	<!--初始化OrderService-->
	public interface OrderService {
	    /**
	     * 初始化订单
	     * @param userId
	     * @return List<UserAddress>
	     */
	    public List<UserAddress> initOrder(String userId);
	}

	<!--初始化UserService-->
	public interface UserService {
	    /**
	     * 按照用户id返回所有的收货地址
	     * @param userId
	     * @return List<UserAddress>
	     */
	    public List<UserAddress> getUserAddressList(String userId);
	}
