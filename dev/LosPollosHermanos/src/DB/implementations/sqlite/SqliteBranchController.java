package DB.implementations.sqlite;

import DB.interfaces.IBranchController;
import DB.util.HibernateUtil;
import Models.DBModels.transport.Branch;
import org.hibernate.Session;

import java.util.ArrayList;

public class SqliteBranchController implements IBranchController {
    private static SqliteBranchController instance;

    public static SqliteBranchController getInstance() {
        if (instance == null) {
            instance = new SqliteBranchController();
        }
        return instance;
    }

    @Override
    public int addBranch(Branch branch) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(branch);
            session.getTransaction().commit();
            return branch.getId();
        }
    }

    @Override
    public boolean updateBranch(Branch branch, int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Branch existing = session.get(Branch.class, id);
            if (existing == null) return false;
            existing.copyFromOther(branch);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean deleteBranch(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Branch branch = session.get(Branch.class, id);
            if (branch != null) {
                session.remove(branch);
                session.getTransaction().commit();
                return true;
            }
            return false;
        }
    }

    @Override
    public Branch getBranchById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Branch.class, id);
        }
    }

    @Override
    public Branch getBranchByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Branch WHERE branchName = :name", Branch.class)
                    .setParameter("name", name)
                    .uniqueResult();
        }
    }

    @Override
    public Branch getBranchByAddress(String address) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Branch WHERE address = :address", Branch.class)
                    .setParameter("address", address)
                    .uniqueResult();
        }
    }

    @Override
    public Branch getBranchByPhoneNumber(String phoneNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Branch WHERE phone = :phone", Branch.class)
                    .setParameter("phone", phoneNumber)
                    .uniqueResult();
        }
    }

    @Override
    public ArrayList<Branch> getBranchesByIds(ArrayList<Integer> ids) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (ArrayList<Branch>) session.createQuery("FROM Branch WHERE id IN :ids", Branch.class)
                    .setParameterList("ids", ids)
                    .list();
        }
    }

    @Override
    public ArrayList<Branch> getAllRecords() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (ArrayList<Branch>) session.createQuery("FROM Branch", Branch.class).list();
        }
    }
}
