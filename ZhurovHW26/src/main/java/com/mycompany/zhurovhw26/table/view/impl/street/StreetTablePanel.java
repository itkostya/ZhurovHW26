package com.mycompany.zhurovhw26.table.view.impl.street;

import java.util.List;
import com.mycompany.zhurovhw26.dao.abstracts.AbstractDAO;
import com.mycompany.zhurovhw26.dao.DatasourceDAO;
import com.mycompany.zhurovhw26.dao.databaseimpl.h2.StreetH2DAOImpl;
import com.mycompany.zhurovhw26.dao.databaseimpl.mongo.StreetMongoDAOImpl;
import com.mycompany.zhurovhw26.dao.databaseimpl.mysql.StreetMySQLDAOImpl;
import com.mycompany.zhurovhw26.dao.datafileimpl.csv.StreetCsvDAOImpl;
import com.mycompany.zhurovhw26.dao.datafileimpl.json.StreetJsonDAOImpl;
import com.mycompany.zhurovhw26.dao.datafileimpl.xml.StreetXmlDAOImpl;
import com.mycompany.zhurovhw26.entity.Street;
import com.mycompany.zhurovhw26.table.view.TablePanel;

public class StreetTablePanel extends TablePanel<Street> {

    public StreetTablePanel() {
        super(new StreetTableModelContainer(), new StreetMySQLDAOImpl(), new CreateStreetDialog());
    }

   @Override
    public AbstractDAO<Street> getCurrentImpl(DatasourceDAO dao) {

        switch (dao) {
            case MySQL:
                return new StreetMySQLDAOImpl();
            case H2:
                return new StreetH2DAOImpl();
            case MongoDB:                
                return new StreetMongoDAOImpl();
             case CSV:
                return new StreetCsvDAOImpl();
            case XML:
                return new StreetXmlDAOImpl();                
            case JSON:
                return new StreetJsonDAOImpl();
            default:
                return new StreetMySQLDAOImpl();
        }

    }

    @Override
    public void changeDBAndData() {

        AbstractDAO<Street> daoStreet = getCurrentImpl(getCurrentDB());

        daoStreet.getConnection();
        daoStreet.checkMetaData();
        setDao(daoStreet);
        setConnectionExists(true);

        List<Street> data = daoStreet.Read();
        refreshDataView(data);
    }

    
}
