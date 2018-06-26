package com.msapps.property.rental;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.msapps.property.rental.Transaction.Category;
import com.msapps.property.rental.Transaction.Type;
import com.msapps.property.rental.db.TransactionDao;
import com.msapps.property.rental.db.UserDao;
import com.msapps.property.rental.db.UserPaymentDao;
import com.msapps.property.rental.db.mongo.TransactionDaoMongoImpl;
import com.msapps.property.rental.db.mongo.TransactionMongoRepository;
import com.msapps.property.rental.db.mongo.UserDaoMongoImpl;
import com.msapps.property.rental.db.mongo.UserMongoRepository;
import com.msapps.property.rental.db.mongo.UserPaymentDaoMongoImpl;
import com.msapps.property.rental.db.mongo.UserPaymentMongoRepository;
import com.msapps.property.rental.db.sql.TransactionDaoSqlImpl;
import com.msapps.property.rental.db.sql.TransactionSqlRepository;
import com.msapps.property.rental.db.sql.UserDaoSqlImpl;
import com.msapps.property.rental.db.sql.UserPaymentDaoSqlImpl;
import com.msapps.property.rental.db.sql.UserPaymentSqlRepository;
import com.msapps.property.rental.db.sql.UserSqlRepository;

@CrossOrigin
@RestController
public class TransactionController {
    @Value("${spring.data.db.selection}")
    private String dbSelection = "mongo";
    
//    private TransactionSqlRepository transRepository;
//    private UserSqlRepository userRepository;
    private UserPaymentSqlRepository userPaymentRepository;
    private CustomerRepository customerRepository;
    
 //   private MongoTemplate mongoTemplate;

    private UserDao userDao;
    private UserPaymentDao userPaymentDao;
    private TransactionDao transactionDao;

    public TransactionController(TransactionSqlRepository transRepository, UserSqlRepository userRepository, UserPaymentSqlRepository userPaymentRepository, 
                                MongoTemplate mongoTemplate,
                                TransactionMongoRepository transMongoRepository, UserMongoRepository userMongoRepository, UserPaymentMongoRepository userPaymentMongoRepository) {
        if (dbSelection.equals("mongo")) {
            this.userDao = new UserDaoMongoImpl(mongoTemplate, userMongoRepository);
            this.userPaymentDao = new UserPaymentDaoMongoImpl(mongoTemplate, userPaymentMongoRepository);
            this.transactionDao = new TransactionDaoMongoImpl(mongoTemplate, transMongoRepository);
        } else {
  //      this.transRepository = transRepository;
  //      this.userRepository = userRepository;
            this.userDao = new UserDaoSqlImpl(userRepository);
            this.userPaymentDao = new UserPaymentDaoSqlImpl(userPaymentRepository);
            this.transactionDao = new TransactionDaoSqlImpl(transRepository);
        }
        this.userPaymentRepository = userPaymentRepository;
    }
  //  @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/transactions")
    public Collection<Transaction> getTransactions() {
        Calendar calendarToday = Calendar.getInstance();

        Calendar calendarYearStart = Calendar.getInstance();
        int currYear = calendarToday.get(Calendar.YEAR)-1;
        calendarYearStart.set(Calendar.DATE, 1);
        calendarYearStart.set(Calendar.MONTH, Calendar.JANUARY);
        calendarYearStart.set(Calendar.YEAR, currYear);

        java.sql.Date today = new java.sql.Date(calendarToday.getTimeInMillis());
        java.sql.Date yearStart = new java.sql.Date(calendarYearStart.getTimeInMillis());

        return transactionDao.findByDateBetweenOrderByDateDesc(yearStart, today);//.stream()
    }
    @PostMapping(path = "/post-transaction", consumes = "application/json", produces = "application/json")
    public Transaction postMethod(@RequestBody Transaction transaction) {
        TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
        long offset = Math.abs(tz.getOffset(new java.util.Date().getTime()));
        long utildateTime = transaction.getDateTime().getTime();
        long adjustedSqlTime = new java.sql.Date(utildateTime + offset).getTime();
        transaction.setDate(new java.sql.Date(adjustedSqlTime));        
        transactionDao.save(transaction);
        return transaction;
    }
    @GetMapping("/users")
    public Collection<User> getUsers() {
        return this.userDao.getUsers();
    }
    @GetMapping("/users/{name}")
    public User getUser(@PathVariable(value="name") String name) {
       return this.userDao.getUser(name);
    }
    @PostMapping(path = "/post-user", consumes = "application/json", produces = "application/json")
    public User postMethod(@RequestBody User user) {
        this.userDao.save(user);
        return user;
    }
    @PostMapping(path = "/post-payment", consumes = "application/json", produces = "application/json")
    public UserPayment postMethod(@RequestBody UserPayment payment) {
        payment.setDate(new java.sql.Date(payment.getDateTime().getTime()));
        userPaymentDao.save(payment);
        return payment;
    }
    @GetMapping("/transactions-sum")
    public double getSum() {
        double overallSum = 0;
        for (TransactionCostSum sum : transactionDao.sum()) {
            if (sum.get_id().equals("Credit")) {
                overallSum -= sum.getTotal();
            }
            if (sum.get_id().equals("Debit")) {
                overallSum += sum.getTotal();
            }
        }
       return overallSum;
    }
    
    private void incOrDecMonth(Calendar cal, int count) {
        // Count is the increment or decrement count
        if (count == 0) {
            return;
        }
        int countVal = java.lang.Math.abs(count);
        int currMonth = cal.get(Calendar.MONTH);
        int currYear = cal.get(Calendar.YEAR);
        
        int numYears = countVal/12;
        int numMonths = countVal%12;
        int newMonth;

        if (count < 0) {
            boolean adjustYear = (currMonth - numMonths) < Calendar.JANUARY;
            if (adjustYear) {
                newMonth = 12 - java.lang.Math.abs(currMonth - numMonths);
                numYears--;
            } else {
                newMonth = currMonth - numMonths;
            }
        } else {
            boolean adjustYear = (currMonth + numMonths) > Calendar.DECEMBER;
            if (adjustYear) {
                newMonth = (currMonth + numMonths) % 12;
                numYears++;
            } else {
                newMonth = currMonth + numMonths;
            }
            newMonth = java.lang.Math.abs(currMonth + numMonths);
        }
        cal.set(Calendar.MONTH, newMonth);
        cal.set(Calendar.YEAR, currYear-numYears);           
    }
    
    private Calendar getBillingStart(User user) {
        Calendar calToday = Calendar.getInstance();
        Calendar calBillingStart = Calendar.getInstance();
        int currMonth = calToday.get(Calendar.MONTH);
        int currYear = calToday.get(Calendar.YEAR);
        
        calBillingStart.set(Calendar.DATE, 25);
                
        switch (user.getPaymentType()) {
        case Monthly:
            if (calToday.get(Calendar.DATE) < 25) {
                incOrDecMonth(calBillingStart, -1);
            }
            break;
            
        case BiMonthly:
            boolean payOnEvenMonth = (user.getFirstMonthOfPayment() % 2) == 0;
            boolean currEven = (currMonth % 2) == 0;
            if (payOnEvenMonth) {
                if (!currEven && calToday.get(Calendar.DATE) >= 25) {
                    calBillingStart.set(Calendar.MONTH, currMonth);
                    calBillingStart.set(Calendar.YEAR, currYear);
                } else {
                    // Get last even month
                    if(!currEven) {
                        //Decrement by 2 months 
                        incOrDecMonth(calBillingStart, -2);
                    } else {
                        //Only decrement by 1 month
                        incOrDecMonth(calBillingStart, -1);
                    }
                }
            } else {
                //Pay on odd month
                if (currEven && calToday.get(Calendar.DATE) >= 25) {
                    calBillingStart.set(Calendar.MONTH, currMonth);
                    calBillingStart.set(Calendar.YEAR, currYear);
                } else {
                    // Get last even month
                    if(currEven) {
                        //Decrement by 2 months 
                        incOrDecMonth(calBillingStart, -2);
                    } else {
                        //Only decrement by 1 month
                        incOrDecMonth(calBillingStart, -1);
                    }
                }
            }
            break;
            
        case Annual:
            break;
        }
        return calBillingStart;
    }
    private Calendar getDueDate(User user) {
        Calendar calToday = Calendar.getInstance();
        Calendar calDueDate = Calendar.getInstance();
        int date = calToday.get(Calendar.DATE);
        int month = calToday.get(Calendar.MONTH);
        int year = calToday.get(Calendar.YEAR);
        
        calDueDate.set(Calendar.DATE, 1);
        
        switch (user.getPaymentType()) {
        case Monthly:
            if (date >= 25) {
                incOrDecMonth(calDueDate, 1);
            }
            break;
            
        case BiMonthly:
            boolean payOnEvenMonth = (user.getFirstMonthOfPayment() % 2) == 0;
            boolean currEven = (month % 2) == 0;
            if (payOnEvenMonth) {
                if (!currEven && calToday.get(Calendar.DATE) >= 25) {
                    incOrDecMonth(calDueDate, 1);
                } else {
                    if(!currEven) {
                        incOrDecMonth(calDueDate, -1);
                    }
                }
            } else {
                if (currEven && calToday.get(Calendar.DATE) >= 25) {
                    incOrDecMonth(calDueDate, 1);
                } else {
                    if(currEven) {
                        incOrDecMonth(calDueDate, -1);
                    }
                }
            }
            break;
            
        case Annual:
            calDueDate.set(Calendar.MONTH, Calendar.JANUARY);
            calDueDate.set(Calendar.YEAR, year++);
            break;
        }
        return calDueDate;
    }

    private enum PaymentStatus {PENDING, PAID};

 //   @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/user-amount-due/{name}")
    public String getAmountDue(@PathVariable(value="name") String name) {
       User user = this.userDao.getUser(name);
       Collection<UserPayment> userPayments = userPaymentDao.getUserPayments(name);
       double paidThisPeriod = 0.0;
       double amountPaidUp = 0.0;
       
       Calendar calBillingStart = getBillingStart(user);

       for (UserPayment userPayment : userPayments) {
           amountPaidUp += userPayment.getAmount();
           Calendar userPaymentCal = Calendar.getInstance();
           userPaymentCal.setTime(userPayment.getDate());
           //Fix this to not include the time in milliseconds when comparing...
           int paymentDate = userPaymentCal.get(Calendar.DATE);
           int billingStartDate = calBillingStart.get(Calendar.DATE);
           int paymentMonth = userPaymentCal.get(Calendar.MONTH);
           int billingStartMonth = calBillingStart.get(Calendar.MONTH);
           int paymentYear = userPaymentCal.get(Calendar.YEAR);
           int billingStartYear = calBillingStart.get(Calendar.YEAR);
           if (paymentYear > billingStartYear || (paymentYear == billingStartYear && (paymentMonth > billingStartMonth || (paymentMonth == billingStartMonth && paymentDate >= billingStartDate)))) {
               paidThisPeriod += userPayment.getAmount();
           }
       }
       PaymentStatus paymentStatus = PaymentStatus.PENDING;
       if (paidThisPeriod >= user.getPaymentLimit()) {
           paymentStatus = PaymentStatus.PAID;
       }
       double amountDue = getSum() * user.getShare() - amountPaidUp;
    //   Date currDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
              
       Calendar calendarDueDate = getDueDate(user);
    //   Date dueDate = new Date(calendarDueDate.getTimeInMillis());
       String monthDisplay = calendarDueDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
       String dateDisplay = monthDisplay + " " + calendarDueDate.get(Calendar.DATE) + ", " + calendarDueDate.get(Calendar.YEAR);
    //   String response = "{\"name\":\"Judith\",\"dueDate\":" + new java.sql.Date(Calendar.getInstance().getTimeInMillis()) + ",\"amountDue\":\"" + amountDue + "\"}";
       String response = "{\"dueDate\":\"" + dateDisplay + "\",\"name\":\"" + name + "\",\"amountDue\":\"" + new DecimalFormat("0.00").format(amountDue) +  
               "\",\"minPayment\":\"" + new DecimalFormat("#.##").format(user.getPaymentLimit()) + "\",\"amountPaidUp\":\"" + new DecimalFormat("0.00").format(amountPaidUp) + 
               "\",\"paidThisMonth\":\"" +  new DecimalFormat("0.00").format(paidThisPeriod) + "\",\"status\":" + paymentStatus.ordinal() + "}";
       return response;
     //  return amountDue;

       
    }

}
