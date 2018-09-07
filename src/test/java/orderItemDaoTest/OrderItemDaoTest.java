package orderItemDaoTest;

import javax.inject.Inject;

import org.junit.Test;

import com.futengwl.dao.OrderItemDao;

import ut.BaseTest;

public class OrderItemDaoTest extends BaseTest {

	
	@Inject
	OrderItemDao orderItemDao;
	
	@Test
	public void orderItemDaoTest() {
		System.out.println(orderItemDao.queryOrderItemCount(17558L, "201807261011010"));
	}
	
}
