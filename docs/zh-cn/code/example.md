```java
public class SystemController extends ThinkerController{

    @RequestMapping(value = "/members.vue")
    public String members()  {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/system/members")
                    .setEditUrl("/thinker/system/membersEdit.vue");

            thinkerTable.column("id", "id");
            thinkerTable.column("username", "账户名");
            thinkerTable.column("op", "操作").edit().delete().setWidth("180px");

            thinkerTable.toolbar().add().delete().defaultTools();
        }).page().toString();
    }
    
    @RequestMapping(value = "/membersEdit.vue")
    public String membersEdit() {
        return ThinkerAdmin.form(thinkerForm -> {
            thinkerForm.input("username", "账户名");
            thinkerForm.input("password", "密码").setType(Input.InputType.PASSWORD);
            thinkerForm.input("remarks", "备注").setType(Input.InputType.TEXTAREA);

            thinkerForm.select("groupIds", "对应角色组").setOptions(
                    SpringContext.getBean(TkGroupsImpl.class).query().eq("status", 1).list()
                            .stream().map(tkGroups -> LabelValue.create(tkGroups.getTitle(), tkGroups.getId()))
                            .collect(Collectors.toList())
            ).setMultiple(true).createDisabled();

            thinkerForm.switchs("status", "角色状态").createDisabled();
        }).setSubmitUrl("/restful/thinker/system/members").page().toString();
    }
}
```