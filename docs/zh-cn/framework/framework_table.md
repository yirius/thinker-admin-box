## ThinkerTable的使用

> ThinkerTable需要设置setApi，才能去读取restful数据，若不设置，则显示[]空数组
> 
```java
public class TestController extends ThinkerController {

    @RequestMapping(value = "/test.vue")
    public String test() {
        // 声明一个thinkerTable
        return ThinkerAdmin.table(thinkerTable -> {
            // 设置api路径
            thinkerTable.setApi("/restful/__PATH__/test")
                    // 设置编辑界面路径
                    .setEditUrl("/__PATH__/testEdit.vue");

            // 添加一列
            thinkerTable.column("id", "id");
            thinkerTable.column("username", "账户名");
            
            // 添加操作列，edit编辑按钮，delete删除按钮，设置宽度180px
            thinkerTable.column("op", "操作").edit().delete().setWidth("180px");

            // 添加toolbar，设置添加/批量删除按钮，使用默认工具[导出+刷新]
            thinkerTable.toolbar().add().delete().defaultTools();
        }).page().toString();
    }
}
```
