package org.lightjason.trafficsimulation.math;

import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.CauchyDistribution;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.GumbelDistribution;
import org.apache.commons.math3.distribution.LaplaceDistribution;
import org.apache.commons.math3.distribution.LevyDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.LogisticDistribution;
import org.apache.commons.math3.distribution.NakagamiDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.ParetoDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.distribution.WeibullDistribution;

import java.text.MessageFormat;
import java.util.Locale;


/**
 * distribution factory
 */
public enum EDistributionFactory
{
    BETA,
    CAUCHY,
    CHI_SQUARED,
    EXPONENTIAL,
    F,
    GAMMA,
    GUMBEL,
    LAPLACE,
    LEVY,
    LOGISTIC,
    LOG_NORMAL,
    NAKAGAMI,
    NORMAL,
    PARETO,
    T,
    TRIANGULAR,
    UNIFORM,
    WEIBULL;

    /**
     * generate the distribution
     *
     * @param p_args distribution arguments
     * @return the distribution
     */
    public final AbstractRealDistribution generate( final double... p_args )
    {
        switch ( this )
        {
            case BETA:
                return new BetaDistribution( p_args[0], p_args[1] );
            case CAUCHY:
                return new CauchyDistribution( p_args[0], p_args[1] );
            case CHI_SQUARED:
                return new ChiSquaredDistribution( p_args[0] );
            case EXPONENTIAL:
                return new ExponentialDistribution( p_args[0] );
            case F:
                return new FDistribution( p_args[0], p_args[1] );
            case GAMMA:
                return new GammaDistribution( p_args[0], p_args[1] );
            case GUMBEL:
                return new GumbelDistribution( p_args[0], p_args[1] );
            case LAPLACE:
                return new LaplaceDistribution( p_args[0], p_args[1] );
            case LEVY:
                return new LevyDistribution( p_args[0], p_args[1] );
            case LOGISTIC:
                return new LogisticDistribution( p_args[0], p_args[1] );
            case LOG_NORMAL:
                return new LogNormalDistribution( p_args[0], p_args[1] );
            case NAKAGAMI:
                return new NakagamiDistribution( p_args[0], p_args[1] );
            case NORMAL:
                return new NormalDistribution( p_args[0], p_args[1] );
            case PARETO:
                return new ParetoDistribution( p_args[0], p_args[1] );
            case T:
                return new TDistribution( p_args[0] );
            case TRIANGULAR:
                return new TriangularDistribution( p_args[0], p_args[1], p_args[2] );
            case UNIFORM:
                return new UniformRealDistribution( p_args[0], p_args[1] );
            case WEIBULL:
                return new WeibullDistribution( p_args[0], p_args[1] );

            default:
                throw new RuntimeException( MessageFormat.format( "not set {0}", this ) );
        }
    }

    /**
     * get enum from string
     *
     * @param p_name string name
     * @return distribution
     */
    public static EDistributionFactory from( final String p_name )
    {
        return EDistributionFactory.valueOf( p_name.toUpperCase( Locale.ROOT ) );
    }
}
