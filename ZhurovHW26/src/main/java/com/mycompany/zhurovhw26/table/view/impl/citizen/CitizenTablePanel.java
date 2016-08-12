package com.mycompany.zhurovhw26.table.view.impl.citizen;

import java.util.List;
import com.mycompany.zhurovhw26.dao.abstracts.AbstractDAO;
import com.mycompany.zhurovhw26.dao.DatasourceDAO;
import com.mycompany.zhurovhw26.dao.databaseimpl.h2.CitizenH2DAOImpl;
import com.mycompany.zhurovhw26.dao.databaseimpl.mongo.CitizenMongoDAOImpl;
import com.mycompany.zhurovhw26.dao.databaseimpl.mysql.CitizenMySQLDAOImpl;
import com.mycompany.zhurovhw26.dao.datafileimpl.csv.CitizenCsvDAOImpl;
import com.mycompany.zhurovhw26.dao.datafileimpl.json.CitizenJsonDAOImpl;
import com.mycompany.zhurovhw26.dao.datafileimpl.xml.CitizenXmlDAOImpl;
import com.mycompany.zhurovhw26.entity.Citizen;
import com.mycompany.zhurovhw26.table.view.TablePanel;

public class CitizenTablePanel extends TablePanel<Citizen> {

    public CitizenTablePanel() {
        super(new CitizenTableModelContainer(), new CitizenMySQLDAOImpl(), new CreateCitizenDialog());
    }

    @Override
    public AbstractDAO<Citizen> getCurrentImpl(DatasourceDAO dao) {

        switch (dao) {
            case MySQL:
                return new CitizenMySQLDAOImpl();
            case H2:
                return new CitizenH2DAOImpl();
            case MongoDB:
                return new CitizenMongoDAOImpl();
            case CSV:
                return new CitizenCsvDAOImpl();
            case XML:
                return new CitizenXmlDAOImpl();                
            case JSON:                
                return new CitizenJsonDAOImpl();
            default:
                return new CitizenMySQLDAOImpl();
        }

    }

    @Override
    public void changeDBAndData() {

        AbstractDAO<Citizen> daoCitizen = getCurrentImpl(getCurrentDB());

        daoCitizen.getConnection();
        daoCitizen.checkMetaData();
        setDao(daoCitizen);
        setConnectionExists(true);

        List<Citizen> data = daoCitizen.Read();
        refreshDataView(data);
    }

}
