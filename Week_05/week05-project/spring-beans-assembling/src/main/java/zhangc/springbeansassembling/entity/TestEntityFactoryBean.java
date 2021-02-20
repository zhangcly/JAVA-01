package zhangc.springbeansassembling.entity;

import org.springframework.beans.factory.FactoryBean;

public class TestEntityFactoryBean implements FactoryBean<TestEntity> {
    @Override
    public TestEntity getObject() throws Exception {
        return new TestEntity();
    }

    @Override
    public Class<?> getObjectType() {
        return TestEntity.class;
    }
}
