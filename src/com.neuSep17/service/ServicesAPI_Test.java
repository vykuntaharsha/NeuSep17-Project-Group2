import java.util.Collection;

public interface ServicesAPI_Test {
  
  //vehicle API
	public Collection<Vehicle> getInventory();
	int getTotalVehicleAmount();
  int getTotalIncentiveAmount();
	void saveVehicle(Vehicle vehicle);
  void deleteVehicle(Vehicle vehicle);
  public Collection<Vehicle> searchVehicleByMake(String make);
  public Collection<Vehicle> sortVehicleByPrice();
  
  //Incentive API
  public Collection<Incentive> getIncentive();
	int getTotalIncentiveAmount();
	void saveIncentive(Incentive incentive);
  void deleteIncentive(Incentive incentive);
  public Collection<Incentive> searchIncentiveByDiscountAmount(int discountAmount);
  public Collection<Incentive> sortIncentiveByDiscountAmount(int discountAmount);
  
	void save();

}
