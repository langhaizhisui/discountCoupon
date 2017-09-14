package cn.lhzs.common.util;

import java.math.BigDecimal;

public class FloatUtil {

	public static Float plus(Float a,Float b){
		return (a == null ? 0 : a) + (b == null ? 0 : b);
	}
	
	public static Integer plus(Integer a,Integer b){
		return (a == null ? 0 : a) + (b == null ? 0 : b);
	}
	
	public static Integer plus(Integer a,Integer b,Integer... c){
		Integer s =  plus(a, b);
		for (Integer c1 : c){
			s = plus(s, c1);
		}
		return s;
	}
	
	public static Float plus(Float a,Float b,Float... c){
		Float s =  plus(a, b);
		for (Float c1 : c){
			s = plus(s, c1);
		}
		return s;
	}
	
	public static Float div(Integer a,Integer b){
		if (null == a){
			a = 0;
		}
		if (null == b){
			b = 1;
		}
		if (b == 0){
			return 0F;
		}
		return Math.round(a * 100 / b)/100F;
	}
	
	public static Float div(Float a,Integer b){
		if (null == a){
			a = 0F;
		}
		if (null == b){
			b = 1;
		}
		if (b == 0){
			return 0F;
		}
		return Math.round(a * 100 / b)/100F;
	}
	public static Float div(Float a,Float b){
		if (null == a){
			a = 0F;
		}
		if (null == b){
			b = 1F;
		}
		if (b == 0){
			return 0F;
		}
		return Math.round(a * 100 / b)/100F;
	}
	
	public static String float2String(Float f,int newScale){
		String ret = "0.00";
		if (null == f){
			if (newScale < 1){
				return "0";
			}else{
				StringBuffer s = new StringBuffer("0.");
				for (int i = 0; i < newScale; i++){
					s.append("0");
				}
				ret = s.toString();
			}
		}else{
			ret =  new BigDecimal(f).setScale(newScale,  BigDecimal.ROUND_HALF_UP).toString();
		}
		return ret;
	}
	
	public static Float toFixed(Float f,int newScale){
		return new BigDecimal(f).setScale(newScale,   BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/**
	 * 浮点型减法计算, 修改不能出现负数，如果是负数，返回0
	 * @param a
	 * @param b
	 * @return
	 */
	public static Float subtract(Float a, Float b){
		Float c = (a == null ? 0f : a) - (b == null ? 0f : b);
		return  c < 0f ? 0f : c;
	}


	public static Float subtract(Float a, Float b, Float... c){
		Float s = subtract(a, b);
		for (Float c1 : c){
			s = subtract(s, c1);
		}
		return s;
	}
	
	public static Integer subtract(Integer a, Integer b){
		return (a == null ? 0 : a) - (b == null ? 0 : b);
	}
	
	public static Integer subtract(Integer a, Integer b, Integer... c){
		Integer s = subtract(a, b);
		for (Integer c1 : c){
			s = subtract(s, c1);
		}
		return s;
	}
	public static Float multip(Float a,Float b,Float... c){
		Float s = multip(a, b);
		for (Float c1 : c){
			s = multip(s, c1);
		}
		return s;
	}
	public static Float multip(Float a,Float b){
		return (a == null ? 0 : a) * (b == null ? 0 : b);
	}
	public static Float multip(Float a,Integer b){
		return (a == null ? 0 : a) * (b == null ? 0 : b);
	}
	public static Integer multip(Integer a,Integer b){
		return (a == null ? 0 : a) * (b == null ? 0 : b);
	}
}
