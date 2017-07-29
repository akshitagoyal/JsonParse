import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Jparser {

	public static void main(String[] args) throws IOException 
	{
		FileReader f=new FileReader("src/Sample_File/new.json");
		ArrayList<Character> string=new ArrayList<Character>();
		int read;
		char c;
		while((read=f.read())!=-1)
		{
			c=(char)read;
			if(!(Character.isWhitespace(c)))
			{
				string.add(c);
			}
		}
	
		System.out.println(string);
		f.close();
			S sobj=new S();
			int x=0;
			try {
				x = sobj.start(string,0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
			finally
			{
			if((x+1)!=string.size())
			{
				System.out.println("Invalid");
			}
			else
			{
				System.out.println("valid");
			}
			}
		
		
	}
	
}
class S
{
	public int start(ArrayList<Character> list,int index) throws Exception
	{
		if(list.get(index)=='{')
		{
			index++;
			if(list.get(index)=='}')
			{
				//System.out.println("Valid ");
				return index;
			}
			else
			{
				E eobj=new E();
				index=eobj.matchE(list,index);
				//index++;
				if(list.get(index)=='}')
				{
					//System.out.println("Valid }");
					return index;
				}
				else
				{
					//System.out.println("Invalid at S } missing");
					throw new bracesMisException("Invalid at S } missing at "+(index+1));
				}
			}
				
		}
		return index;
	}
}

class E
{
	
	public int matchE(ArrayList<Character> list,int index) throws Exception
	{
		if(list.get(index)=='"')
		{
			index++;
			Alpha aobj=new Alpha();
			index=aobj.matchAlpha(list, index);
			if(list.get(index)=='"')
			{
				index++;
				
			}
			else
			{
				//System.out.println("Invalid at E ....'' missing");
				//throw exception
				throw new eQuotesMisException("Invalid at E ....'' missing at "+(index+1));
			}
			if(list.get(index)==':')
			{
				index++;
				V vobj=new V();
				index=vobj.matchV(list, index);
			}
			else
			{
				//System.out.println("Invalid at E : missing");
				//throw exception
				throw new colonMisException("Invalid at E : missing at "+(index+1));
			}
			if(list.get(index)==',')
			{
				index++;
				E eobj1=new E();
				index=eobj1.matchE(list, index);
			}
			else
			{
				return index;
			}
			
		}
		else
		{
			//System.out.println("Invalid at E ''.... missing")
			  throw new sQuotesMisException("Invalid at E ''........ missing at "+(index+1));
		}
		return index;
	}
}
class Alpha
{	
	public int matchAlpha(ArrayList<Character> list,int index) throws Exception
	{
		while(list.get(index)!='"')
		{
						index++;
						if(index==list.size())
						{
							index=index-1;
							throw new InvalidStrException("Invalid String");
						}

		}
		return index;
	}
}
class V
{
	public int matchV(ArrayList<Character> list,int index) throws Exception
	{
		if(list.get(index)=='"')
		{
			index++;
			Alpha aobj=new Alpha();
			index=aobj.matchAlpha(list, index);
			if(list.get(index)=='"')
			{
				index++;
				return index;
			}
			else
			{
				//System.out.println("Invalid at V ....'' missing");
				//System.out.println(list.get(index));
				throw new equotesException("Invalid at V ....'' missing at "+(index+1));
			}
		}
		
		if(list.get(index)=='{')
		{
			index++;
			E eobj=new E();
			index=eobj.matchE(list,index);
			//index++;
			if(list.get(index)=='}')
			{
				index++;
				//System.out.println("Valid }");
				return index;
			}
			else
			{
				//System.out.println("Invalid at S } missing");
				throw new bracesMisException("Invalid at S } missing at "+(index+1));
			}
		}
		if(list.get(index)=='[')
		{
			index++;
			A aobj=new A();
			index=aobj.matchA(list,index);
			if(list.get(index)==']')
			{
				index++;
				return index;
			}
			else
			{
				//System.out.println("] missing");
				throw new bracketMisException("] missing at "+(index+1));
			}
		}
		if(Character.isDigit(list.get(index))||Character.isLetter(list.get(index)))
		{
			C cobj=new C();
			index=cobj.matchC(list,index);
		}
		
		else
		{
			//System.out.println("value missing");
			throw new ValueMisException("Value missing at "+(index+1));
		}		
		return index;
	}
}
class A
{
	public int matchA(ArrayList<Character> list,int index) throws Exception
	{
			if(list.get(index)==']')
			{
				return index;
			}
			else
			{
				V vobj=new V();
				index=vobj.matchV(list, index);
				if(list.get(index)==',')
				{
					index++;
					A obj=new A();
					index=obj.matchA(list,index);
				
				}
				else
				{
					return index;
				}
			}
			return index;
	}
}
class C
{
	public int matchC(ArrayList<Character> list,int index) throws Exception
	{
		if(Character.isDigit(list.get(index)))
		{
			while(Character.isDigit(list.get(index)))
				
					{
							index++;
					}
			return index;
	
		}
		if(list.get(index)=='n'||list.get(index)=='t'||list.get(index)=='f')
		{
			if(list.get(index)=='n')
			{
				index++;
				if(list.get(index)=='u'&&list.get(index+1)=='l'&&list.get(index+2)=='l')
				{
					index=index+3;
				}
				return index;
				
			}
			if(list.get(index)=='t')
			{
				index++;
				if(list.get(index)=='r'&&list.get(index+1)=='u'&&list.get(index+2)=='e')
				{
					index=index+3;
				}
				return index;
				
			}
			if(list.get(index)=='f')
			{
				index++;
				if(list.get(index)=='a'&&list.get(index+1)=='l'&&list.get(index+2)=='s'&&list.get(index+3)=='e')
				{
					index=index+4;
				}
				return index;
			}
		}
			/*if(Character.isLetter(list.get(index)))
			{
				String str="";
				while(list.get(index)!=','||list.get(index)!='}')
				{
					str=str+list.get(index);
					index++;
					if(index==list.size())
					{
						throw new InvalidConsException("Invalid constant at "+(index+1));
					}
			
				}
				if(str.equals("null")||str.equals("true")||str.equals("false"))
				{
					return index;
				}
				else
				{
					throw new InvalidConsException("Invalid constant at "+(index+1));
				}
			}*/
			else
			{//System.out.println("invalid constant");
				throw new InvalidConsException("Invalid constant at "+(index+1));
			}
		return index;
		
	}
}



	
