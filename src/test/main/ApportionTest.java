package test.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agi.hcms.apportion.AllocationFactory;
import org.agi.hcms.apportion.IAllocation;
import org.agi.hcms.apportion.IAllocation.Parameter;
import org.agi.hcms.apportion.IAllocation.Result;

public class ApportionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IAllocation ia = AllocationFactory.getCalculater(AllocationFactory.HeatmeterLAW);
		List<Parameter> parameters = new ArrayList<>();
		Parameter p = new IAllocation().new Parameter();
		p.area = 100;
		p.timeOn = 10;
		p.timeOff = 10;
		p.tempOn = 25;
		p.tempOff = 25;
		parameters.add(p);
		System.out.println("test");
		List<Result> result = ia.alloca(100, 10, parameters);
		for (Result r : result) {
			System.out.println(r.flowing + " " + r.heating + " " + r.openTime);
		}
		
		Map<Long, Long> testMap = new HashMap<>();
		testMap.put(1l, 2l);
		for (Map.Entry<Long, Long> entry : testMap.entrySet()) {
			System.out.println(entry.getKey());
			testMap.remove(entry.getKey());
		}
		System.out.println(testMap.size());
	}

}
