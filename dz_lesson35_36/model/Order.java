package dz_lesson35_36.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Order extends IdEntity{

    private static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private User user;
    private Room room;
    private Date dateFrom;
    private Date dateTo;
    private double moneyPaid;

    public Order(){

    }

    public Order(long id, User user, Room room, Date dateFrom, Date dateTo, double moneyPaid) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.moneyPaid = moneyPaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Double.compare(order.moneyPaid, moneyPaid) == 0 &&
                Objects.equals(user, order.user) &&
                Objects.equals(room, order.room) &&
                Objects.equals(dateFrom, order.dateFrom) &&
                Objects.equals(dateTo, order.dateTo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user, room, dateFrom, dateTo, moneyPaid);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserId(long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setRoomId(long id) {
        this.id = id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public double getMoneyPaid() {
        return moneyPaid;
    }

    public void setMoneyPaid(double moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    @Override
    public String toString() {
        String dateStart = FORMAT.format(dateFrom);
        String dateEnd = FORMAT.format(dateTo);
        return id + ","
                + user.getId() + ","
                + room.getId() + ","
                + dateStart + ","
                + dateEnd + ","
                + moneyPaid;
    }
}
