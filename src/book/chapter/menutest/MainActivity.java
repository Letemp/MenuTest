package book.chapter.menutest;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private boolean flag = false;
	TextView tView[]=new TextView[4];//定义一个文本框的数组
	int num;
	
	//为文本框添加上下文菜单
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		int[] files=new int[]{R.id.file01,R.id.file02,R.id.file03,R.id.file04};//各文本框id组成的数组
		for(int i=0;i<tView.length;i++){//初始化数组
			tView[i]=(TextView)this.findViewById(files[i]);//根据id找到对应的文本框
			registerForContextMenu(tView[i]);//为文本框注册上下文菜单
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		   case R.id.start://设置菜单中的启动菜单项
		   case R.id.stop://设置菜单中的禁用菜单项
			   invalidateOptionsMenu();//更新选项菜单
			   break;
		   case R.id.exit:
			   finish();
			   break;
		   default:
			   break;
		}
		Toast.makeText(MainActivity.this,item.getTitle()+"被单击了！",1000).show();
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onPrepareOptionsMenu(menu);
		MenuItem start=menu.findItem(R.id.start);//获取启动菜单项
		MenuItem stop=menu.findItem(R.id.stop);//获取禁用菜单项
		start.setEnabled(flag);//设置启动菜单项的状态
		stop.setEnabled(!flag);//设置禁用菜单项的状态，和启用项互斥
		flag=!flag;//每次变化时，改变标志量
		return true;
		
	}

	//真正创建上下文菜单的代码放在下面方法中
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		switch(v.getId()){//判断需要注册上下文菜单的控件
		case R.id.file01:
			num = 1;//num值为一，主要为后面的id服务
			break;
		case R.id.file02:
			num = 2;
			break;
		case R.id.file03:
			num = 3;
			break;
		case R.id.file04:
			num = 4;
			break;
		default:
			break;
		}
		menu.setHeaderTitle("文件操作");//上下文菜单的标题
		menu.add(0,Menu.FIRST+num*10+1,0,"发送");//添加发送菜单项
		SubMenu subMenu=menu.addSubMenu(0,Menu.FIRST+num*10+2,1,"设置文字的颜色");//添加子菜单
		subMenu.setHeaderTitle("The Second Level Menu");//子菜单的标题
		//子菜单添加子项
		subMenu.add(0,Menu.FIRST+num*100+21,0,"红色");
		subMenu.add(0,Menu.FIRST+num*100+22,0,"黄色");
		subMenu.add(0,Menu.FIRST+num*100+23,0,"绿色");
		//添加菜单项
		menu.add(0,Menu.FIRST+num*10+3,2,"重命名");
		menu.add(0,Menu.FIRST+num*10+4,3,"删除");
		super.onCreateContextMenu(menu, v, menuInfo);//调用父类的该方法
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		String mesString="你选择的是";//显示提示信息
		int count=item.getItemId()-Menu.FIRST;
		num=count/10;//计算num的值
		if(num>10){
			num=num/10;//确保num值与上面一致
		}
		if(item.getItemId()==(Menu.FIRST+num*10+1)){//是否选择发送菜单项
			mesString+="发送";//拼接消息的值
		}else if(item.getItemId()==(Menu.FIRST+num*10+2)){//是否选择颜色菜单项
			mesString+="进入颜色设置界面";//拼接消息的值
		}else if(item.getItemId()==(Menu.FIRST+num*10+21)){//是否选择红色
			tView[num-1].setTextColor(Color.RED);//设置对应文本框的颜色
		}else if(item.getItemId()==(Menu.FIRST+num*10+22)){//是否选择蓝色
			tView[num-1].setTextColor(Color.BLUE);//设置对应文本框的颜色
		}else if(item.getItemId()==(Menu.FIRST+num*10+23)){//是否选择绿色
			tView[num-1].setTextColor(Color.GREEN);//设置对应文本框的颜色
		}else if(item.getItemId()==(Menu.FIRST+num*10+3)){
			final EditText inputname=new EditText(this);//创建一个文本编辑框
			AlertDialog bDialog=new AlertDialog.Builder(MainActivity.this)
			.setIcon(android.R.drawable.btn_star)//设置对话框图标
			.setTitle("请输入名字")//设置对话框标题
			.setView(inputname)//设置对话框显示的控件
			.setPositiveButton("确定",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					tView[num-1].setText(inputname.getText().toString());
				}
			}).setNegativeButton("取消",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub	
				}
			}).create();
			bDialog.show();
			mesString+="重命名成功";
		}else if(item.getItemId()==(Menu.FIRST+num*10+4)){
			mesString+="删除";
		}
		Toast.makeText(this, mesString,Toast.LENGTH_LONG ).show();
		return true;
	}

	
	

}
