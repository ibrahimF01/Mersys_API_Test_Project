package Campus.Model;

public class BankClass {

    private String id = null;
    private String name ;
    private String iban ;
    private String integrationCode ;
    private String currency = "TRY";
    private boolean active = true;
    private String schoolId = "6343bf893ed01f0dc03a509a";


      public BankClass() {

      }

      public String getId() {
            return id;
      }

      public void setId(String id) {
            this.id = id;
      }

      public String getName() {
            return name;
      }

      public void setName(String name) {
            this.name = name;
      }

      public String getIban() {
            return iban;
      }

      public void setIban(String iban) {
            this.iban = iban;
      }

      public String getIntegrationCode() {
            return integrationCode;
      }

      public void setIntegrationCode(String integrationCode) {
            this.integrationCode = integrationCode;
      }

      public String getCurrency() {
            return currency;
      }

      public void setCurrency(String currency) {
            this.currency = currency;
      }

      public boolean isActive() {
            return active;
      }

      public void setActive(boolean active) {
            this.active = active;
      }

      public String getSchoolId() {
            return schoolId;
      }

      public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
      }
}
